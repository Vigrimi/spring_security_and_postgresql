package vinnikov.inbox.ru.insideControlEx2022jan12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vinnikov.inbox.ru.insideControlEx2022jan12.entity.MessageMyUser;
import vinnikov.inbox.ru.insideControlEx2022jan12.repository.MessageRepository;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static java.lang.Thread.sleep;

@Service
public class MessageService
{
    private MessageRepository messageRepository;
    private final String fileName = "historyten.txt";

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void saveLast10MsgsFm(long totalQtyMsgs)
    {
        long tenMsgs = 10L;
        if(totalQtyMsgs < tenMsgs)
        {
            tenMsgs = totalQtyMsgs;
        }
        String textForFile = "";
        for (long i = totalQtyMsgs; i > (totalQtyMsgs - tenMsgs); i--)
        {
            int id = (int) i;
            MessageMyUser msg = messageRepository.findById(id);
            System.out.println("***id:" + id);
            System.out.println("***msg:" + msg);
            textForFile = textForFile + msg.getMessage_my_user() + ";";
        }
        System.out.println("---------textForFile:" + textForFile);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,false));
            sleep(200);
            writer.write(textForFile);
            sleep(200);
            writer.flush();
            sleep(1_000);
            writer.close();
            sleep(200);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
