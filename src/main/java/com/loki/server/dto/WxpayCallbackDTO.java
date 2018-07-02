package com.loki.server.dto;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
public class WxpayCallbackDTO {
	private String return_code;

    private String return_msg;

    public String getReturn_msg() {
        return return_msg;
    }
    @XmlElement
    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getReturn_code() {
        return return_code;
    }
    @XmlElement
    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    @Override
    public String toString() {
        String result = "";
        try {
            JAXBContext context = JAXBContext.newInstance(WxpayCallbackDTO.class);
            Marshaller mar = context.createMarshaller();
            StringWriter writer = new StringWriter();
            mar.marshal(this, writer);
            result = writer.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }
}
