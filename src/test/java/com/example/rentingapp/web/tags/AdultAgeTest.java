package com.example.rentingapp.web.tags;

import jakarta.servlet.jsp.JspContext;
import jakarta.servlet.jsp.JspWriter;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AdultAgeTest {

    @Test
    void testDoTag()  {
        JspWriter out = new CustomJspWriter(JspWriter.DEFAULT_BUFFER, true);
        JspContext jspContext = mock(JspContext.class);
        when(jspContext.getOut()).thenReturn(out);
        AdultAgeTag adultAgeTag=new AdultAgeTag();
        adultAgeTag.setJspContext(jspContext);
        assertDoesNotThrow(adultAgeTag::doTag);
        assertEquals(LocalDate.now().minusYears(18).toString(), out.toString());
    }

}
