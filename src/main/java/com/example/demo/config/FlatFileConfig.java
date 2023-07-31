package com.example.demo.config;

import com.example.demo.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class FlatFileConfig {

  private static final Logger LOGGER = LoggerFactory.getLogger(FlatFileConfig.class);


  public FlatFileConfig() {
    LOGGER.info("FlatFileReaderConfig class has been instantiated.");
  }

  @Bean
  FlatFileItemReader<User> reader(){
    FlatFileItemReader<User> reader = new FlatFileItemReader<>();

    //Set the file Location
    reader.setResource(new ClassPathResource("MOCK_DATA.csv"));
    reader.setLinesToSkip(1);

    //Set the liner mapper
    DefaultLineMapper<User> lineMapper = new DefaultLineMapper<>();
    DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
    tokenizer.setNames("id","first_name","last_name","email","gender");// Set the columns names
    lineMapper.setLineTokenizer(tokenizer);

    lineMapper.setFieldSetMapper(new UserFieldSetMapper());

    reader.setLineMapper(lineMapper);

    return reader;
  }

  @Bean
  public FlatFileItemReader<User> itemReader(){

    return new FlatFileItemReaderBuilder<User>()
            .name("userItemReader")
            .resource(new ClassPathResource("MOCK_DATA.csv"))
            .delimited()
            .names("id","first_name","last_name","email","gender")
            .fieldSetMapper(new UserFieldSetMapper())
            .build();
  }

  @Bean
  public Step stepDecompressor(StepBuilderFactory stepBuilderFactory,
                               DecompressTasklet decompressor ){

    return stepBuilderFactory
            .get("stepDecompressor")
            .tasklet(decompressor)
            .build();
  }

  @Bean
  public JobRepository jobRepositoryFactory(DataSource dataSource) throws Exception{
    JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
    jobRepositoryFactoryBean.setDataSource(dataSource);
    jobRepositoryFactoryBean.setDatabaseType("H2");

    return jobRepositoryFactoryBean.getObject();

  }

  @Bean
  public SimpleJobLauncher jobLauncher(JobRepository jobRepository, Job job) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException{

    SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
    simpleJobLauncher.setJobRepository(jobRepository);

    return simpleJobLauncher;
  }

  @Bean
  public Step step(StepBuilderFactory stepBuilderFactory, ItemWriter<User> itemWriter,
                   FlatFileItemReader<User> reader, DecompressTasklet decompressor,
                   JobRepository jobRepository){
    return stepBuilderFactory
            .get("step")
            .<User,User>chunk(10)
            .reader(reader)
            .writer(itemWriter).repository(jobRepository)
            .build();

  }

  @Bean
  Job job(Step step ,JobBuilderFactory jobBuilderFactory){
    return jobBuilderFactory
            .get("job")
            .start(step)
            .build();
  }

}
