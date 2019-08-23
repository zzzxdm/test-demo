package com.zzz.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Document(indexName = "idx_goos", type = "doc", shards = 1, replicas = 0)
public class Goods {

    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text, analyzer = "ik", searchAnalyzer = "ik")
    private String desc;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Text)
    private String category;

    @Field(type = FieldType.Long)
    private Long inventory;

    @DateTimeFormat(pattern = "yyyy-MM-dd\'T\'HH:mm:ss.SSS")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Field(type = FieldType.Date)
    private LocalDateTime addTime;
}
