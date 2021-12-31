package hu.gemesi.taskmaker.common.dto.xml.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.OffsetDateTime;

public class OffsetDateTimeAdapter extends XmlAdapter<OffsetDateTime, XMLGregorianCalendar> {

    @Override
    public XMLGregorianCalendar unmarshal(OffsetDateTime offsetDateTime) throws Exception {
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(offsetDateTime.toString());
    }

    @Override
    public OffsetDateTime marshal(XMLGregorianCalendar xmlGregorianCalendar) throws Exception {
        return xmlGregorianCalendar.toGregorianCalendar().toZonedDateTime().toOffsetDateTime();
    }
}
