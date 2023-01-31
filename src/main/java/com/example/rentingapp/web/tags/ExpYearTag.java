package com.example.rentingapp.web.tags;

import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;
import java.time.LocalDate;

public class ExpYearTag extends SimpleTagSupport {
    @Override
    public void doTag() throws IOException {
        JspWriter out= getJspContext().getOut();
        String expYear= String.valueOf(LocalDate.now().plusYears(5).getYear());
        out.print(expYear);
    }
}
