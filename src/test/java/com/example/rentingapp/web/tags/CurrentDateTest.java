package com.example.rentingapp.web.tags;

import jakarta.servlet.jsp.JspContext;
import jakarta.servlet.jsp.JspWriter;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CurrentDateTest {


    @Test
    void testDoTag()  {
        JspWriter out = new CustomJspWriter(JspWriter.DEFAULT_BUFFER, true);
        JspContext jspContext = mock(JspContext.class);
        when(jspContext.getOut()).thenReturn(out);
        CurrentDateTag currentDateTag = new CurrentDateTag();
        currentDateTag.setJspContext(jspContext);
        assertDoesNotThrow(currentDateTag::doTag);
        assertEquals(LocalDate.now().toString(), out.toString());
    }


}
