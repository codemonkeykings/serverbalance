package com.tsingsoft;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import sun.net.httpserver.HttpServerImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;

/**
 * 一句话功能简述.
 *
 * @author ZhangGe
 * @version v1.0
 * @create 2017年12月11日 22:22
 */
@WebServlet(name = "dispacherServlet", urlPatterns = "/dispacher")
public class DispacherServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        URL url = new URL("http://localhost:8080" + req.getRequestURI());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        if (conn.getResponseCode() == 200) {
            try (InputStream in = conn.getInputStream();
                 ByteArrayOutputStream bous = new ByteArrayOutputStream();
                 OutputStream out = resp.getOutputStream();) {
                byte[] buffer = new byte[1024];
                int len = -1;
                while ((len = in.read(buffer)) != -1) {
                    bous.write(buffer, 0, len);
                }
                out.write(bous.toByteArray());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> paramMap = getParameterFromPost(req);
        doGet(req, resp);
    }

    private Map<String, String> getParameterFromPost(HttpServletRequest req) {
        try (InputStream in = req.getInputStream();
             ByteArrayOutputStream bous = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = in.read(buffer)) != -1) {
                bous.write(buffer, 0, len);
            }
            bous.flush();
            System.out.println(bous.toString());
            return convertMap(bous.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }

    }

    private Map<String, String> convertMap(String queryParam) {
        if (queryParam != null && queryParam.length() != 0) {

        }
        return Collections.emptyMap();
    }
}
