package Server.Model;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;

public record ChatMessage(@Expose String sender, @Expose String content) {

    @Override
    public String toString() {
        return "From "+this.sender+":\n"+this.content;
    }
}
