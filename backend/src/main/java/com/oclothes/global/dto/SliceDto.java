package com.oclothes.global.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Getter
@Builder(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SliceDto<T> {
    private int pageNumber;
    private int contentsCount;
    @JsonProperty("isFirst")
    private boolean first;
    @JsonProperty("isLast")
    private boolean last;
    @JsonProperty("hasNext")
    @Accessors(fluent = true)
    private boolean hasNext;
    @JsonProperty("isEmpty")
    private boolean empty;
    private Sort sort;
    private List<T> contents;

    public static <T> SliceDto<T> create(Slice<T> slice) {
        return SliceDto.<T>builder()
                .pageNumber(slice.getNumber() + 1)
                .contentsCount(slice.getContent().size())
                .first(slice.isFirst())
                .last(slice.isLast())
                .hasNext(slice.hasNext())
                .empty(slice.isEmpty())
                .sort(slice.getSort())
                .contents(slice.getContent())
                .build();
    }

}
