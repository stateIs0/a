package com.ipzoe.light.test.entity;

import java.time.LocalDateTime;

/**
 * Created by cxs on 2017/4/14.
 */
public class Base {

    Long id;

    LocalDateTime create_time;

    LocalDateTime update_time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreate_time() {
        return create_time;
    }

    public void setCreate_time(LocalDateTime create_time) {
        this.create_time = create_time;
    }

    public LocalDateTime getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(LocalDateTime update_time) {
        this.update_time = update_time;
    }
}
