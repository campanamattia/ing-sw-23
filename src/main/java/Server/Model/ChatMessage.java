package Server.Model;

import java.time.LocalDateTime;

public record ChatMessage(String sender, String content, LocalDateTime time) {

    @Override
    public String toString() {
        return "From "+this.sender+":\n"+this.content+"\n"+this.time.toString();
    }
}
