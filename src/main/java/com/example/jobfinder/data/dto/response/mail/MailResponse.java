package com.example.jobfinder.data.dto.response.mail;


import com.example.jobfinder.constant.ApiURL;
import com.example.jobfinder.utils.enumeration.EMailType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Slf4j
@Builder
public class MailResponse {
    private String namePost;
    private String nameReceive;
    private String to;
    private String subject;
    private String mailTemplate;
    private String cv;
    private EMailType typeMail;

    private String token;


    public void createMailConfirm(String urlRedirect, String token) {

        String link = urlRedirect + ApiURL.CANDIDATE + "/active?active-token=" + token;
        log.info(link, "link active");
        this.mailTemplate = "<!DOCTYPE html>\n" +
                "<html lang=\"vi\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\n" +
                "    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\n" +
                "    <link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500&display=swap\" rel=\"stylesheet\">\n" +
                "    <title>Email</title>\n" +
                "    <style>\n" +
                "        .email-container {\n" +
                "            width: 50%;\n" +
                "            padding: 0px;\n" +
                "            margin: 20px auto;\n" +
                "            font-family: 'Poppins', sans-serif;\n" +
                "            color: #000;\n" +
                "            font-size: 15px;\n" +
                "        }\n" +
                "\n" +
                "        img {\n" +
                "            width: 100%;\n" +
                "            height: 80%;\n" +
                "        }\n" +
                "\n" +
                "        .email-container__new-pass {\n" +
                "            width: 40%;\n" +
                "            margin: 20px auto;\n" +
                "            text-align: center;\n" +
                "            background-color: #FE5656;\n" +
                "            border-radius: 15px;\n" +
                "            color: #000;\n" +
                "            border: none;\n" +
                "        }\n" +
                "\n" +
                "        .btn-redirect {\n" +
                "            background-color: #f4b459;\n" +
                "            border: none;\n" +
                "        }\n" +
                "\n" +
                "        .txt-redirect {\n" +
                "            color: white !important;\n" +
                "            text-decoration: none;\n" +
                "            display: block;\n" +
                "            padding: 20px 0;\n" +
                "            font-size: 18px;\n" +
                "        }\n" +
                "\n" +
                "        h4 {\n" +
                "            font-size: 20px;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "\n" +
                "        span {\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "\n" +
                "        h5 {\n" +
                "            line-height: 5px;\n" +
                "            font-size: 15px;\n" +
                "        }\n" +
                "\n" +
                "        @media screen and (max-width: 750px) {\n" +
                "            .email-container {\n" +
                "                width: 90%;\n" +
                "            }\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <div class=\"email-container\">\n" +
                "        <div class=\"email-container__image\">\n" +
                "            <img src=\"https://firebasestorage.googleapis.com/v0/b/job-worked.appspot.com/o/images%2Flogo.png?alt=media&token=17d1b6d9-0d85-45b9-9c5b-7b35d00ab50f\"\n" +
                "                alt=\"Hình ảnh quên mật khẩu\">\n" +
                "        </div>\n" +
                "        <div class=\"email-container__content\">\n" +
                "            <h3 class=\"email-container__title\">Chào mừng bạn đến với <strong>DreamxWork</strong></h3>\n" +
                "            <p><strong>DreamxWork</strong> là một nền tảng tuyển dụng công việc thực tập dành cho ngành công nghệ thông\n" +
                "                tin tại Việt Nam. Mang đến cho các bạn sinh viên hoặc các bạn mới ra trường có cơ hội tiếp cận các công\n" +
                "                việc thực tập nhanh chóng và dễ dàng.</p>\n" +
                "            <p>Để hoàn tất quá trình đăng ký tài khoản, mời bạn nhấp chuột vào nút bên dưới để kích hoạt tài khoản.</p>\n" +
                "            <button class=\"email-container__new-pass\">\n" +
                "                <a class=\"txt-redirect\" href=\"" + link + "\">Xác nhận email</a>\n" +
                "            </button>\n" +
                "            <p>Cảm ơn bạn đã sử dụng dịch vụ của DreamxWork</p>\n" +
                "            <p>Thân mến</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
    }

    public void createMailForgotPassword(String urlRedirect, String token) {

        String link = urlRedirect + ApiURL.USER + "/active-forget-password?token=" + token;
        log.info(link, "link forget password");
        this.mailTemplate = "<!DOCTYPE html>\n" +
                "<html lang=\"vi\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\n" +
                "    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\n" +
                "    <link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500&display=swap\" rel=\"stylesheet\">\n" +
                "    <title>Email</title>\n" +
                "    <style>\n" +
                "        .email-container {\n" +
                "            width: 50%;\n" +
                "            padding: 30px 0px;\n" +
                "            margin: 20px auto;\n" +
                "            font-family: 'Poppins', sans-serif;\n" +
                "            color: #555;\n" +
                "            font-size: 15px;\n" +
                "        }\n" +
                "\n" +
                "        img {\n" +
                "            width: 100%;\n" +
                "            height: 80%;\n" +
                "        }\n" +
                "\n" +
                "        .email-container__new-pass {\n" +
                "            width: 40%;\n" +
                "            margin: 20px auto;\n" +
                "            text-align: center;\n" +
                "            background-color: #FE5656;\n" +
                "            border-radius: 15px;\n" +
                "            color: #000;\n" +
                "            border: none;\n" +
                "        }\n" +
                "\n" +
                "        .btn-redirect {\n" +
                "            background-color: #00b074 !important;\n" +
                "            border: none;\n" +
                "        }\n" +
                "\n" +
                "        .txt-redirect {\n" +
                "            color: white !important;\n" +
                "            text-decoration: none;\n" +
                "            display: block;\n" +
                "            padding: 20px 0;\n" +
                "            font-size: 18px;\n" +
                "        }\n" +
                "\n" +
                "        h4 {\n" +
                "            font-size: 20px;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "\n" +
                "        span {\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "\n" +
                "        h5 {\n" +
                "            line-height: 5px;\n" +
                "            font-size: 15px;\n" +
                "        }\n" +
                "\n" +
                "        @media screen and (max-width: 750px) {\n" +
                "            .email-container {\n" +
                "                width: 90%;\n" +
                "            }\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <div class=\"email-container\">\n" +
                "        <div class=\"email-container__image\">\n" +
                "            <img src=\"https://firebasestorage.googleapis.com/v0/b/job-worked.appspot.com/o/images%2Flogo.png?alt=media&token=17d1b6d9-0d85-45b9-9c5b-7b35d00ab50f\"\n" +
                "                alt=\"Hình ảnh quên mật khẩu\">\n" +
                "        </div>\n" +
                "        <div class=\"email-container__content\">\n" +
                "            <h1 class=\"email-container__title\">Bạn quên mật khẩu?</h1>\n" +
                "            <p>Chúng tôi nhận được yêu cầu bạn muốn đổi mật khẩu tài khoản trên <strong>DreamxWork</strong></p>\n" +
                "            <p>Để thay đổi mật khẩu, bạn vui lòng bấm vào nút dưới đây:</p>\n" +
                "            <button class=\"email-container__new-pass\">\n" +
                "                <a class=\"txt-redirect\" href=\""+ link +"\">Đặt lại mật khẩu</a>\n" +
                "            </button>\n" +
                "            <p>Email yêu cầu đổi mật khẩu này sẽ hết hạn trong 10 phút.</p>\n" +
                "            <p>Nếu không yêu cầu đổi mật khẩu, bạn có thể yên tâm bỏ qua email này. Chỉ người có quyền truy cập vào\n" +
                "                email của bạn mới có thể thay đổi mật khẩu.</p>\n" +
                "            <p>Email yêu cầu đổi mật khẩu này sẽ hết hạn trong 10 phút.</p>\n" +
                "\n" +
                "            <p>Cảm ơn bạn đã sử dụng dịch vụ của DreamxWork</p>\n" +
                "            <p>Thân mến </p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "\n" +
                "</html>";

    }


//    public void createMailActiveOTP(String urlRedirect, int token) {
//        String link = String.valueOf(token);
//        System.out.println("DAY LA LINK NE : " + link);
//        this.mailTemplate = "<!DOCTYPE html>\r\n"
//                + "<html lang=\"en\">\r\n"
//                + "<head>\r\n"
//                + "    <meta charset=\"UTF-8\">\r\n"
//                + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
//                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
//                + "    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\r\n"
//                + "<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\r\n"
//                + "<link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500&display=swap\" rel=\"stylesheet\">\r\n"
//                + "    <title>Email</title>\r\n"
//                + "    <style>\r\n"
//                + "        .email-container{\r\n"
//                + "            width: 50%;\r\n"
//                + "            padding: 30px 0px;\r\n"
//                + "            margin: 20px auto;\r\n"
//                + "            font-family: 'Poppins', sans-serif;\r\n"
//                + "            color: #555;\r\n"
//                + "            font-size: 15px\r\n"
//                + "        }\r\n"
//                + "        img {\r\n"
//                + "            width: 100%;\r\n"
//                + "            height: 80%;\r\n"
//                + "        }\r\n"
//                + "        .email-container__new-pass{\r\n"
//                + "            width: 40%;\r\n"
//                + "            margin: 20px auto;\r\n"
//                + "            text-align: center;\r\n"
//                + "            /* padding: 5px 0px; */\r\n"
//                + "            /* border: 2px solid #f4b459; */\r\n"
//                + "            background-color: #00b074;\r\n"
//                + "            border-radius: 15px;\r\n"
//                + "            color: #000;\r\n"
//                + "            border: none;\r\n"
//                + "        }\r\n"
//                + "        .btn-redirect{\r\n"
//                + "            background-color: #00b074!important;\r\n"
//                + "            border: none;\r\n"
//                + "        }\r\n"
//                + "        .txt-redirect{\r\n"
//                + "           color: white !important;\r\n"
//                + "            text-decoration: none;\r\n"
//                + "            display: block;\r\n"
//                + "            padding: 20px 0;\r\n"
//                + "            font-size: 18px;\r\n"
//                + "        }\r\n"
//                + "        h4{\r\n"
//                + "            font-size: 20px;\r\n"
//                + "            font-weight: bold\r\n"
//                + "        }\r\n"
//                + "        span{\r\n"
//                + "            font-weight: bold \r\n"
//                + "        }\r\n"
//                + "        h5{\r\n"
//                + "            line-height: 5px;\r\n"
//                + "            font-size: 15px;\r\n"
//                + "        }\r\n"
//                + "\r\n"
//                + "        @media screen and (max-width: 750px) {\r\n"
//                + "            .email-container {\r\n"
//                + "                width: 90%;\r\n"
//                + "            }\r\n"
//                + "        }\r\n"
//                + "    </style>\r\n"
//                + "</head>\r\n"
//                + "<body>\r\n"
//                + "    <div class=\"email-container\">\r\n"
//                + "        <div class=\"email-container__image\">\r\n"
//                + "            <img src=\"https://firebasestorage.googleapis.com/v0/b/storageimg-36153.appspot.com/o/images%2FLOGO_JOB.jpg?alt=media&token=ffd185c2-5b6f-4441-8038-37b6e1f40e0b\" alt=\"image-forgot-password\">\r\n"
//                + "        </div>\r\n"
//                + "        <div class=\"email-container__content\">\r\n"
//                + "            <h1 class=\"email-container__title\">Bạn xác thực tài khoản?</h1>\r\n"
//                + "            <p>Chúng tôi nhận được yêu cầu bạn muốn xác nhận tài khoản trên <strong>App Jobsit</strong></p>\r\n"
//                + "            <p>Để xác nhận email, bạn vui lòng bấm vào nút dưới đây:</strong></p>\r\n"
//                + "            <h3 class=\"email-container__title\">" + token + "</h3>\r\n"
//                + "            <p>Email yêu cầu xác nhận này sẽ hết hạn trong 10 phút.</p>\r\n"
//                + "            <p>Nếu không yêu cầu xác nhận, bạn có thể yên tâm bỏ qua email này. Chỉ người có quyền truy cập vào email của bạn mới có thể xác nhận tài khoản.</p>\r\n"
//                + "            <p>Email yêu cầu xác nhận này sẽ hết hạn trong 10 phút.</p>\r\n"
//                + "            \r\n"
//                + "\r\n"
//                + "            <p>Cảm ơn bạn đã sử dụng dịch vụ của Jobsit.vn</p>\r\n"
//                + "            <p>Thân mến </p>\r\n"
//                + "            <hr>\r\n"
//                + "            <h4>Công ty Cổ phần R2S</h4>\r\n"
//                + "            <h5>Phone: 0919 365 363</h5>\r\n"
//
//                + "            <h5>Email: <a  href=\"mailto:tuyendung@r2s.com.vn<\">tuyendung@r2s.com.vn</a></h5>\r\n"
//
//                + "            <h5>Website: <a href=\"http://jobsit.vn\">jobsit.vn</a> </h5>\r\n"
//                + "            \r\n"
//                + "        </div>\r\n"
//                + "    </div>\r\n"
//                + "</body>\r\n"
//                + "</html>";
//    }
//
//    public void createMailForgotPassword(String urlRedirect, String token) {
//        String link = urlRedirect + "api/user/edit?reset_password_token=" + token;
//        System.out.println("DAY LA LINK NE : " + link);
//        this.mailTemplate = "<!DOCTYPE html>\r\n"
//                + "<html lang=\"en\">\r\n"
//                + "<head>\r\n"
//                + "    <meta charset=\"UTF-8\">\r\n"
//                + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
//                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
//                + "    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\r\n"
//                + "<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\r\n"
//                + "<link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500&display=swap\" rel=\"stylesheet\">\r\n"
//                + "    <title>Email</title>\r\n"
//                + "    <style>\r\n"
//                + "        .email-container{\r\n"
//                + "            width: 50%;\r\n"
//                + "            padding: 30px 0px;\r\n"
//                + "            margin: 20px auto;\r\n"
//                + "            font-family: 'Poppins', sans-serif;\r\n"
//                + "            color: #555;\r\n"
//                + "            font-size: 15px\r\n"
//                + "        }\r\n"
//                + "        img {\r\n"
//                + "            width: 100%;\r\n"
//                + "            height: 80%;\r\n"
//                + "        }\r\n"
//                + "        .email-container__new-pass{\r\n"
//                + "            width: 40%;\r\n"
//                + "            margin: 20px auto;\r\n"
//                + "            text-align: center;\r\n"
//                + "            /* padding: 5px 0px; */\r\n"
//                + "            /* border: 2px solid #f4b459; */\r\n"
//                + "            background-color: #00b074;\r\n"
//                + "            border-radius: 15px;\r\n"
//                + "            color: #000;\r\n"
//                + "            border: none;\r\n"
//                + "        }\r\n"
//                + "        .btn-redirect{\r\n"
//                + "            background-color: #00b074!important;\r\n"
//                + "            border: none;\r\n"
//                + "        }\r\n"
//                + "        .txt-redirect{\r\n"
//                + "           color: white !important;\r\n"
//                + "            text-decoration: none;\r\n"
//                + "            display: block;\r\n"
//                + "            padding: 20px 0;\r\n"
//                + "            font-size: 18px;\r\n"
//                + "        }\r\n"
//                + "        h4{\r\n"
//                + "            font-size: 20px;\r\n"
//                + "            font-weight: bold\r\n"
//                + "        }\r\n"
//                + "        span{\r\n"
//                + "            font-weight: bold \r\n"
//                + "        }\r\n"
//                + "        h5{\r\n"
//                + "            line-height: 5px;\r\n"
//                + "            font-size: 15px;\r\n"
//                + "        }\r\n"
//                + "\r\n"
//                + "        @media screen and (max-width: 750px) {\r\n"
//                + "            .email-container {\r\n"
//                + "                width: 90%;\r\n"
//                + "            }\r\n"
//                + "        }\r\n"
//                + "    </style>\r\n"
//                + "</head>\r\n"
//                + "<body>\r\n"
//                + "    <div class=\"email-container\">\r\n"
//                + "        <div class=\"email-container__image\">\r\n"
//                + "            <img src=\"https://firebasestorage.googleapis.com/v0/b/storageimg-36153.appspot.com/o/images%2FLOGO_JOB.jpg?alt=media&token=ffd185c2-5b6f-4441-8038-37b6e1f40e0b\" alt=\"image-forgot-password\">\r\n"
//                + "        </div>\r\n"
//                + "        <div class=\"email-container__content\">\r\n"
//                + "            <h1 class=\"email-container__title\">Bạn quên mật khẩu?</h1>\r\n"
//                + "            <p>Chúng tôi nhận được yêu cầu bạn muốn đổi mật khẩu tài khoản trên <strong>Jobsit.vn</strong></p>\r\n"
//                + "            <p>Để thay đổi mật khẩu, bạn vui lòng bấm vào nút dưới đây:</strong></p>\r\n"
////                + "           <button class=\"btn-redirect\"> \r\n"
//                + "            <button class=\"email-container__new-pass\">\r\n"
////				+ "                <h4>"+token+"</h4>\r\n"
//                + "               <a class=\"txt-redirect\" href=" + link + ">Đặt lại mật khẩu</a>\r\n"
//
//                + "            </button>\r\n"
////                + "            </button>\r\n"
//                + "            <p>Email yêu cầu đổi mật khẩu này sẽ hết hạn trong 10 phút.</p>\r\n"
//                + "            <p>Nếu không yêu cầu đổi mật khẩu, bạn có thể yên tâm bỏ qua email này. Chỉ người có quyền truy cập vào email của bạn mới có thể thay đổi mật khẩu.</p>\r\n"
//                + "            <p>Email yêu cầu đổi mật khẩu này sẽ hết hạn trong 10 phút.</p>\r\n"
//                + "            \r\n"
//                + "\r\n"
//                + "            <p>Cảm ơn bạn đã sử dụng dịch vụ của Jobsit.vn</p>\r\n"
//                + "            <p>Thân mến </p>\r\n"
//                + "            <hr>\r\n"
//                + "            <h4>Công ty Cổ phần R2S</h4>\r\n"
//                + "            <h5>Phone: 0919 365 363</h5>\r\n"
//
//                + "            <h5>Email: <a  href=\"mailto:tuyendung@r2s.com.vn<\">tuyendung@r2s.com.vn</a></h5>\r\n"
//
//                + "            <h5>Website: <a href=\"http://jobsit.vn\">jobsit.vn</a> </h5>\r\n"
//                + "            \r\n"
//                + "        </div>\r\n"
//                + "    </div>\r\n"
//                + "</body>\r\n"
//                + "</html>";
//    }
//

//
//    public void createTemplateHRApply() {
//        this.mailTemplate = String.format("<!DOCTYPE html>\r\n"
//                + "<html lang=\"en\">\r\n"
//                + "<head>\r\n"
//                + "    <meta charset=\"UTF-8\">\r\n"
//                + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
//                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
//                + "    <title>Document</title>\r\n"
//                + "    <style>\r\n"
//                + "        * {\r\n"
//                + "            padding: 0;\r\n"
//                + "            margin: 0;\r\n"
//                + "        }\r\n"
//                + "\r\n"
//                + "        .email-container {\r\n"
//                + "            padding: 40px;\r\n"
//                + "        }\r\n"
//                + "        p {\r\n"
//                + "            line-height: 30px;\r\n"
//                + "        }\r\n"
//                + "        ul {\r\n"
//                + "            list-style-type: none;\r\n"
//                + "        }\r\n"
//                + "        li {\r\n"
//                + "            font-weight: bold;\r\n"
//                + "            line-height: 30px;\r\n"
//                + "        }\r\n"
//                + "        a {\r\n"
//                + "            color: #2d8ada;\r\n"
//                + "        }\r\n"
//                + "    </style>\r\n"
//                + "</head>\r\n"
//                + "<body>\r\n"
//                + "    <div class=\"email-container\">\r\n"
//                + "        <p>\r\n"
//                + "            Thân chào bạn <br/>\r\n"
//                + "             IT InternShip Jobs gửi đến bạn thông báo về HR <b> %s </b> vừa quan tâm đến bài Tuyển dụng <b> %s </b> được đăng vào ngày %d/%d/%d vào lúc %d: %d. <br/>\r\n"
//                + "            Danh sách sinh viên được gửi kèm theo Email này, Partner cần thường xuyên cập nhật thông tin bài đăng trên trang website <br/>\r\n"
//                + "            Đây là email tự động, bạn không cần reply mail này.<br/>\r\n"
//                + "            Thân mến.<br/>\r\n"
//                + "        </p>\r\n"
//                + "        <div class=\"line\">______</div>\r\n"
//                + "        <ul>\r\n"
//                + "           <li>Công ty cổ phần R2S</li> \r\n"
//                + "           <li>Phone: 0919 365 363</li>\r\n"
//                + "           <li>Email: tuyendung@r2s.com.vn</li>\r\n"
//                + "           <li>Website: <a href=\"https://jobsit.vn\">https://jobsit.vn</a> ; <a href=\"https://r2s.edu.vn\">https://r2s.edu.vn</a> </li>\r\n"
//                + "        </ul>\r\n"
//                + "<br><img src='cid:R2S'/>"
//                + "    </div>\r\n"
//                + "</body>\r\n"
//                + "</html>", nameReceive, namePost, LocalDate.now().getDayOfMonth(), LocalDate.now().getMonth().getValue(), LocalDate.now().getYear(), LocalDateTime.now().getHour(), LocalDateTime.now().getMinute());
//
//    }
//
//    public void createTemplateActive() {
//        String human = "";
//        switch (typeMail) {
//            case ActiveCompany:
//                human = "HR";
//                break;
//            case ActiveUniversity:
//                human = "Partner";
//                break;
//            default:
//                return;
//        }
//        this.mailTemplate = String.format("<!DOCTYPE html>\r\n"
//                + "<html>\r\n"
//                + "\r\n"
//                + "<head>\r\n"
//                + "    <title></title>\r\n"
//                + "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n"
//                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n"
//                + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\r\n"
//                + "    <style type=\"text/css\">\r\n"
//                + "        @media screen {\r\n"
//                + "            @font-face {\r\n"
//                + "                font-family: 'Lato';\r\n"
//                + "                font-style: normal;\r\n"
//                + "                font-weight: 400;\r\n"
//                + "                src: local('Lato Regular'), local('Lato-Regular'), url(https://fonts.gstatic.com/s/lato/v11/qIIYRU-oROkIk8vfvxw6QvesZW2xOQ-xsNqO47m55DA.woff) format('woff');\r\n"
//                + "            }\r\n"
//                + "\r\n"
//                + "            @font-face {\r\n"
//                + "                font-family: 'Lato';\r\n"
//                + "                font-style: normal;\r\n"
//                + "                font-weight: 700;\r\n"
//                + "                src: local('Lato Bold'), local('Lato-Bold'), url(https://fonts.gstatic.com/s/lato/v11/qdgUG4U09HnJwhYI-uK18wLUuEpTyoUstqEm5AMlJo4.woff) format('woff');\r\n"
//                + "            }\r\n"
//                + "\r\n"
//                + "            @font-face {\r\n"
//                + "                font-family: 'Lato';\r\n"
//                + "                font-style: italic;\r\n"
//                + "                font-weight: 400;\r\n"
//                + "                src: local('Lato Italic'), local('Lato-Italic'), url(https://fonts.gstatic.com/s/lato/v11/RYyZNoeFgb0l7W3Vu1aSWOvvDin1pK8aKteLpeZ5c0A.woff) format('woff');\r\n"
//                + "            }\r\n"
//                + "\r\n"
//                + "            @font-face {\r\n"
//                + "                font-family: 'Lato';\r\n"
//                + "                font-style: italic;\r\n"
//                + "                font-weight: 700;\r\n"
//                + "                src: local('Lato Bold Italic'), local('Lato-BoldItalic'), url(https://fonts.gstatic.com/s/lato/v11/HkF_qI1x_noxlxhrhMQYELO3LdcAZYWl9Si6vvxL-qU.woff) format('woff');\r\n"
//                + "            }\r\n"
//                + "        }\r\n"
//                + "\r\n"
//                + "        /* CLIENT-SPECIFIC STYLES */\r\n"
//                + "        body,\r\n"
//                + "        table,\r\n"
//                + "        td,\r\n"
//                + "        a {\r\n"
//                + "            -webkit-text-size-adjust: 100%;\r\n"
//                + "            -ms-text-size-adjust: 100%;\r\n"
//                + "        }\r\n"
//                + "\r\n"
//                + "        table,\r\n"
//                + "        td {\r\n"
//                + "            mso-table-lspace: 0pt;\r\n"
//                + "            mso-table-rspace: 0pt;\r\n"
//                + "        }\r\n"
//                + "\r\n"
//                + "        img {\r\n"
//                + "            -ms-interpolation-mode: bicubic;\r\n"
//                + "        }\r\n"
//                + "\r\n"
//                + "        /* RESET STYLES */\r\n"
//                + "        img {\r\n"
//                + "            border: 0;\r\n"
//                + "            height: auto;\r\n"
//                + "            line-height: 100%;\r\n"
//                + "            outline: none;\r\n"
//                + "            text-decoration: none;\r\n"
//                + "        }\r\n"
//                + "\r\n"
//                + "        table {\r\n"
//                + "            border-collapse: collapse !important;\r\n"
//                + "        }\r\n"
//                + "\r\n"
//                + "        body {\r\n"
//                + "            height: 100% !important;\r\n"
//                + "            margin: 0 !important;\r\n"
//                + "            padding: 0 !important;\r\n"
//                + "            width: 100% !important;\r\n"
//                + "        }\r\n"
//                + "\r\n"
//                + "        /* iOS BLUE LINKS */\r\n"
//                + "        a[x-apple-data-detectors] {\r\n"
//                + "            color: inherit !important;\r\n"
//                + "            text-decoration: none !important;\r\n"
//                + "            font-size: inherit !important;\r\n"
//                + "            font-family: inherit !important;\r\n"
//                + "            font-weight: inherit !important;\r\n"
//                + "            line-height: inherit !important;\r\n"
//                + "        }\r\n"
//                + "\r\n"
//                + "        /* MOBILE STYLES */\r\n"
//                + "        @media screen and (max-width:600px) {\r\n"
//                + "            h1 {\r\n"
//                + "                font-size: 32px !important;\r\n"
//                + "                line-height: 32px !important;\r\n"
//                + "            }\r\n"
//                + "        }\r\n"
//                + "\r\n"
//                + "        /* ANDROID CENTER FIX */\r\n"
//                + "        div[style*=\"margin: 16px 0;\"] {\r\n"
//                + "            margin: 0 !important;\r\n"
//                + "        }\r\n"
//                + "    </style>\r\n"
//                + "</head>\r\n"
//                + "\r\n"
//                + "<body style=\"background-color: #f4f4f4; margin: 0 !important; padding: 0 !important;\">\r\n"
//                + "    <!-- HIDDEN PREHEADER TEXT -->\r\n"
//                + "    <div style=\"display: none; font-size: 1px; color: #fefefe; line-height: 1px; font-family: 'Lato', Helvetica, Arial, sans-serif; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden;\"> We're thrilled to have you here! Get ready to dive into your new account.\r\n"
//                + "    </div>\r\n"
//                + "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n"
//                + "        <!-- LOGO -->\r\n"
//                + "        <tr>\r\n"
//                + "            <td bgcolor=\"#FFA73B\" align=\"center\">\r\n"
//                + "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\r\n"
//                + "                    <tr>\r\n"
//                + "                        <td align=\"center\" valign=\"top\" style=\"padding: 40px 10px 40px 10px;\"> </td>\r\n"
//                + "                    </tr>\r\n"
//                + "                </table>\r\n"
//                + "            </td>\r\n"
//                + "        </tr>\r\n"
//                + "        <tr>\r\n"
//                + "            <td bgcolor=\"#FFA73B\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\r\n"
//                + "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\r\n"
//                + "                    <tr>\r\n"
//                + "                        <td bgcolor=\"#ffffff\" align=\"center\" valign=\"top\" style=\"padding: 40px 20px 20px 20px; border-radius: 4px 4px 0px 0px; color: #111111; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; letter-spacing: 4px; line-height: 48px;\">\r\n"
//                + "                            <h1 style=\"font-size: 48px; font-weight: 400; margin: 2;\">Xin Chúc Mừng!</h1> <img src=\" https://img.icons8.com/clouds/100/000000/handshake.png\" width=\"125\" height=\"120\" style=\"display: block; border: 0px;\" />\r\n"
//                + "                        </td>\r\n"
//                + "                    </tr>\r\n"
//                + "                </table>\r\n"
//                + "            </td>\r\n"
//                + "        </tr>\r\n"
//                + "        <tr>\r\n"
//                + "            <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\r\n"
//                + "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\r\n"
//                + "                    <tr>\r\n"
//                + "                        <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 40px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\r\n"
//                + "                            <p style=\"margin: 0;\">Tài khoản %s của bạn đã được kích hoạt, từ giờ bạn sẽ không bị giới hạn những tính năng dành riêng cho %s. </p>\r\n"
//                + "                        </td>\r\n"
//                + "                    </tr>\r\n"
//                + "                    <tr>\r\n"
//                + "                        <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 40px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\r\n"
//                + "                            <p style=\"margin: 0;\">Chúng tôi mong rằng bạn sẽ có những trải nghiệm tốt nhất khi sử dụng nền tảng <b>jobsit.com</b>.</p>\r\n"
//                + "                        </td>\r\n"
//                + "                    </tr>\r\n"
//                + "                    <tr>\r\n"
//                + "                        <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 30px 40px 30px; border-radius: 0px 0px 4px 4px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\r\n"
//                + "                            <p style=\"margin: 0;\">Mong rằng đôi bên sẽ có sự hợp tác lâu dài,<br>R2S Team</p>\r\n"
//                + "                        </td>\r\n"
//                + "                    </tr>\r\n"
//                + "                </table>\r\n"
//                + "            </td>\r\n"
//                + "        </tr>\r\n"
//                + "        <tr>\r\n"
//                + "            <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 30px 10px 0px 10px;\">\r\n"
//                + "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\r\n"
//                + "                    <tr>\r\n"
//                + "                        <td bgcolor=\"#FFECD1\" align=\"center\" style=\"padding: 30px 30px 30px 30px; border-radius: 4px 4px 4px 4px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\r\n"
//                + "                            <h2 style=\"font-size: 20px; font-weight: 400; color: #111111; margin: 0;\">Bạn cần sự hỗ trợ?</h2>\r\n"
//                + "                            <p style=\"margin: 0;\"><a href=\"#\" target=\"_blank\" style=\"color: #FFA73B;\">Liên hệ với chúng tôi tại đây</a></p>\r\n"
//                + "                        </td>\r\n"
//                + "                    </tr>\r\n"
//                + "                </table>\r\n"
//                + "            </td>\r\n"
//                + "        </tr>\r\n"
//                + "        <tr>\r\n"
//                + "            <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\r\n"
//                + "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\r\n"
//                + "                    <tr>\r\n"
//                + "                        <td bgcolor=\"#f4f4f4\" align=\"left\" style=\"padding: 0px 30px 30px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 400; line-height: 18px;\"> <br>\r\n"
//                + "                            <p style=\"margin: 0;\">Đây là Email tự động vui lòng không reply lại mail này.</p>\r\n"
//                + "                        </td>\r\n"
//                + "                    </tr>\r\n"
//                + "                </table>\r\n"
//                + "            </td>\r\n"
//                + "        </tr>\r\n"
//                + "    </table>\r\n"
//                + "</body>\r\n"
//                + "\r\n"
//                + "</html>", human);
//    }
}
