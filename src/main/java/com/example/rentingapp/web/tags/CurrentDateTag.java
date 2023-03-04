package com.example.rentingapp.web.tags;

import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CurrentDateTag extends SimpleTagSupport {
    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        out.print(formatDate(LocalDate.now()));
    }

    private String formatDate(LocalDate localDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dtf.format(localDate);
    }
}
