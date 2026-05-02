package Services;

import net.engineeringdigest.journalApp.JournalApplication;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;


@SpringBootTest(classes = JournalApplication.class)
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

//    @Disabled
        @Test
        void TestMail() {
            redisTemplate.opsForValue().set("email", "ayushsonone.mbg@gmail.com"); // ← correct?
            Object email = redisTemplate.opsForValue().get("email");
            System.out.println("Stored email: " + email); // add this line
        }
}
