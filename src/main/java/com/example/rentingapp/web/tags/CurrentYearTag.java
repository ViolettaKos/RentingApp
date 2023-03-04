package com.example.rentingapp.web.tags;

import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;
import java.time.LocalDate;


public class CurrentYearTag extends SimpleTagSupport {
    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        String year = String.valueOf(LocalDate.now().getYear());
        out.print(year);
    }

}
