package tech.chickies.notificationserver.mk.util;

public class Utils {
    public static String downloadFileEmailTemplate(String fileLink){
        String emailTemplate = "<html>\n" +
                "    <body style=\"font-family: Arial, sans-serif; background-color: #f2f2f2;\">\n" +
                "        <div style=\"background-color: #ffffff; max-width: 600px; margin: 0 auto; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\">\n" +
                "            <p style=\"font-size: 16px; color: #333; margin: 0;\">Hello,</p>\n" +
                "            <p style=\"font-size: 16px; color: #333; margin-top: 20px;\">Your orders export is ready.</p>\n" +
                "            <p style=\"font-size: 14px; color: #666;\">You can download the file by clicking the link below:</p>\n" +
                "            <a href=\"" + fileLink + "\" style=\"display: inline-block; padding: 10px 20px; background-color: #0077cc; color: #ffffff; text-decoration: none; border-radius: 5px; font-size: 14px;\">" +
                " Download Now</a>\n" +
                "        </div>\n" +
                "    </body>\n" +
                "</html>";

        return emailTemplate;
    }
}
