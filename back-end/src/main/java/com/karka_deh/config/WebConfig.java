
package com.karka_deh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;

@Configuration
// NOTE: there's a warning about PageImpl, this fixes it, but it reduces the
// resulting json
//
// @EnableSpringDataWebSupport(pageSerializationMode =
// PageSerializationMode.VIA_DTO)
public class WebConfig {
}
