package com.antonio.demo.service.event;

import com.antonio.demo.endpoint.event.model.SendEmailRequested;
import com.antonio.demo.mail.Email;
import com.antonio.demo.mail.Mailer;
import jakarta.mail.internet.InternetAddress;
import java.io.File;
import java.util.List;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

@Service
@AllArgsConstructor
public class SendEmailRequestedService implements Consumer<SendEmailRequested> {
  private final Mailer mailer;
  private final S3Client s3Client;
  private final @Value("${aws.s3.bucket}") String bucketName;

  @SneakyThrows
  @Override
  public void accept(SendEmailRequested event) {
    File pdf = null;
    if (event.getPdfKey() != null && !event.getPdfKey().isBlank()) {
      pdf = File.createTempFile("mail-attachment-", ".pdf");
      s3Client.getObject(
          GetObjectRequest.builder().bucket(bucketName).key(event.getPdfKey()).build(),
          pdf.toPath());
    }

    InternetAddress to = new InternetAddress(event.getTo());
    mailer.accept(
        new Email(
            to,
            List.of(),
            List.of(),
            event.getSubject(),
            event.getHtmlBody(),
            pdf != null ? List.of(pdf) : List.of()));
  }
}
