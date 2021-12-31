package hu.gemesi.taskmaker.user.sevice;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        System.out.println(LocalDateTime.now().compareTo(LocalDateTime.now().plusDays(1)));
        /*System.out.println(Hashing.sha256().hashString("user1", StandardCharsets.UTF_8).toString());
        System.out.println(Hashing.sha256().hashString("admin", StandardCharsets.UTF_8).toString());
        System.out.println(Hashing.sha256().hashString("3425", StandardCharsets.UTF_8).toString());
        System.out.println(Hashing.sha256().hashString("user2", StandardCharsets.UTF_8).toString());
        System.out.println(Hashing.sha256().hashString(Hashing.sha512().hashString("gemesisz1", StandardCharsets.UTF_8).toString(), StandardCharsets.UTF_8).toString());
    */
    }
}
