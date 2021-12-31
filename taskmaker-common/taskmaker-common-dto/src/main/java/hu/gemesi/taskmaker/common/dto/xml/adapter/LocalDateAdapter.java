package hu.gemesi.taskmaker.common.dto.xml.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;

public class LocalDateAdapter extends XmlAdapter<LocalDate, XMLGregorianCalendar> {

    @Override
    public XMLGregorianCalendar unmarshal(LocalDate localDate) throws Exception {
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(localDate.toString());
    }

    @Override
    public LocalDate marshal(XMLGregorianCalendar xmlGregorianCalendar) throws Exception {
        return xmlGregorianCalendar.toGregorianCalendar().toZonedDateTime().toOffsetDateTime().toLocalDate();
    }
}
