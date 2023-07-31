package com.example.demo.config;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipInputStream;

@Component
public class DecompressTasklet implements Tasklet {

  private Resource resource;
  private String targetDirectory;
  private String targetFile;

  @Override
  public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception{

    ZipInputStream zipInputStream =
            new ZipInputStream(new BufferedInputStream(resource.getInputStream()));
    File targetDirectoryFile = new File(targetDirectory);
    if(targetDirectoryFile.exists()) {
      FileUtils.forceMkdir(targetDirectoryFile);
    }

    File target = new File(targetDirectory,targetFile);
    BufferedOutputStream dest = null;
    while(zipInputStream.getNextEntry() != null) {
      if(!target.exists()) {
        target.createNewFile();
      }
      FileOutputStream fos = new FileOutputStream(
              target
      );
      dest = new BufferedOutputStream(fos);
      IOUtils.copy(zipInputStream,dest);
      dest.flush();
      dest.close();
    }
    zipInputStream.close();
    if(!target.exists()) {
      throw new IllegalStateException(
              "Could not decompress anything from the archive!");
    }
    return RepeatStatus.FINISHED;
  }


}
