package tech.chickies.notificationserver.mk.consumer;

import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.ListValuedMap;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import tech.chickies.notificationserver.mail.EmailDTO;
import tech.chickies.notificationserver.mail.EmailSendService;
import tech.chickies.notificationserver.mk.dto.MediaResponse;
import tech.chickies.notificationserver.mk.dto.OrderFinalResponse;
import tech.chickies.notificationserver.mk.dto.OrderResponse;
import tech.chickies.notificationserver.mk.dto.ResponseObject;
import tech.chickies.notificationserver.mk.util.Utils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class DemoConsumer {
    private final RestTemplate restTemplate;
    private final EmailSendService emailSendService;
    @Autowired
    public DemoConsumer(RestTemplate restTemplate, EmailSendService emailSendService) {
        this.restTemplate = restTemplate;
        this.emailSendService = emailSendService;
    }
    @KafkaListener(topics = "order-export")
    public void consume(@Payload ExportMessage message) {
        try{
        List<OrderResponse> orders =  new ArrayList<>();
            ResponseObject<List<OrderResponse>>responseOrders;
            int page = 0;
            do {
                String eUri = message.getApiEndpoint();
                if(page > 0) {
                    eUri = updatePageNumber(eUri, page + 1);
                }
                responseOrders = fetchOrderData(eUri);
                page = responseOrders.getPageNumber();
                orders.addAll(responseOrders.getData());
            }while(responseOrders.getPageNumber()*responseOrders.getPageSize() < responseOrders.getTotalCount());
                responseOrders.getData();
            if (orders != null) {
                orders.stream().forEach(o -> System.out.println(o.toString()));
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("OrderData");
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Order ID");
                headerRow.createCell(1).setCellValue("Order No");
                headerRow.createCell(2).setCellValue("Total Quantity");
                headerRow.createCell(3).setCellValue("Total Price");
                headerRow.createCell(4).setCellValue("Surcharge");
                headerRow.createCell(5).setCellValue("Customer Id");
                headerRow.createCell(6).setCellValue("Customer Name");
                headerRow.createCell(7).setCellValue("Customer Email");
                headerRow.createCell(8).setCellValue("Customer Phone");
                headerRow.createCell(9).setCellValue("Meal Name");
                headerRow.createCell(10).setCellValue("Tray Name");
                headerRow.createCell(11).setCellValue("Service From");
                headerRow.createCell(12).setCellValue("Service To");
                headerRow.createCell(13).setCellValue("Kitchen Id");
                headerRow.createCell(14).setCellValue("Kitchen Name");
                headerRow.createCell(15).setCellValue("Order Date");

                int rowNum = 1;
                for (OrderResponse order : orders) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(order.getId());
                    row.createCell(1).setCellValue(order.getNo());
                    row.createCell(2).setCellValue(order.getTotalQuantity());
                    row.createCell(3).setCellValue(order.getTotalPrice());
                    row.createCell(4).setCellValue(order.getSurcharge());
                    row.createCell(5).setCellValue(order.getCustomer().getId());
                    row.createCell(6).setCellValue(order.getCustomer().getUser().getFullName());
                    row.createCell(7).setCellValue(order.getCustomer().getUser().getEmail());
                    row.createCell(8).setCellValue(order.getCustomer().getUser().getPhone());
                    row.createCell(9).setCellValue(order.getMeal().getName());
                    row.createCell(10).setCellValue(order.getMeal().getTray().getName());
                    row.createCell(11).setCellValue(order.getMeal().getServiceFrom());
                    row.createCell(12).setCellValue(order.getMeal().getServiceTo());
                    row.createCell(13).setCellValue(order.getMeal().getKitchen().getId());
                    row.createCell(14).setCellValue(order.getMeal().getKitchen().getName());
                    row.createCell(15).setCellValue(order.getCreatedDate());
                    // Add more data fields as needed
                }
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                workbook.write(outputStream);
                byte[] excelData = outputStream.toByteArray();
                var url = uploadSheetFile(excelData);
                if(url != null && !url.isEmpty()){
                    System.out.println(url);
                    System.out.println("Received message: " + message.getEmailSender());
                    var email = new EmailDTO();
                    email.setContent(Utils.downloadFileEmailTemplate(url));
                    email.setTo(message.getEmailSender());
                    email.setSubject("[Momkitchen] Orders export is ready for download");
                    emailSendService.sendBasicEmail(email);
                }
            }

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public OrderFinalResponse fetchOrderData(String apiUrl) {
        return restTemplate.getForObject(apiUrl, OrderFinalResponse.class);
    }
    private String updatePageNumber(String url, int newPageNumber) {
        String[] parts = url.split("PageNumber=");

        if (parts.length == 2) {
            String[] subparts = parts[1].split("&");

            if (subparts.length == 2) {

                return parts[0] + "PageNumber=" + newPageNumber + "&" + subparts[1];
            }
        }
        return url;
    }

    private String uploadSheetFile (byte[] fileStream){
        // Create a request to upload the Excel file
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ByteArrayResource(fileStream) {
            @Override
            public String getFilename() {
                return "generated.xlsx"; // Set the desired filename
            }
        });
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        String uploadEndpoint = "http://momkitchen.wyvernpserver.tech/api/v1/storage";
        ResponseEntity<ResponseObject> responseEntity = restTemplate.exchange(
                uploadEndpoint,
                HttpMethod.POST,
                requestEntity,
                ResponseObject.class
        );
        var res = (LinkedHashMap<String,String>)responseEntity.getBody().getData();
        return res.get("url");
    }
}
