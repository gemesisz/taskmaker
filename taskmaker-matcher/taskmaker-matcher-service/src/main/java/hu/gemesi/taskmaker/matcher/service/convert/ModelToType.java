package hu.gemesi.taskmaker.matcher.service.convert;

import hu.gemesi.taskmaker.common.model.task.Task;
import hu.gemesi.taskmaker.common.model.task.TaskAssignment;
import hu.gemesi.taskmaker.dto.common.matcher.TaskResponseType;
import hu.gemesi.taskmaker.dto.common.matcher.TaskResultType;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;

public class ModelToType {

    public static TaskResponseType modelToType(Task task, TaskAssignment taskAssignment) {
        return new TaskResponseType().withTaskName(task.getName()).withDescription(task.getDescription()).withPoint(task.getPoint()).withDeadline(dateConverter(task.getDeadline()))
                .withResult(new TaskResultType().withResultComment(taskAssignment.getResultComment()).withUsername(taskAssignment.getUser().getUsername()).withUserPoint(taskAssignment.getPoint()));
    }
    public static TaskResponseType modelToType(Task task) {
        return new TaskResponseType().withTaskName(task.getName()).withDescription(task.getDescription())
                .withPoint(task.getPoint()).withDeadline(dateConverter(task.getDeadline()));
    }

    private static XMLGregorianCalendar dateConverter(LocalDateTime date) {
        try {
            var dateString = date.toString();
            if (date.getSecond() == 0 && date.getNano() == 0) {
                dateString += ":00";
            }
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(dateString);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }
}
