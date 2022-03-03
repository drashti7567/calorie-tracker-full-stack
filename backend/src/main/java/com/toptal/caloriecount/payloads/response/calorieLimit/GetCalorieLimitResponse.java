package com.toptal.caloriecount.payloads.response.calorieLimit;

import com.toptal.caloriecount.payloads.response.misc.MessageResponse;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GetCalorieLimitResponse extends MessageResponse {
    private Float calories;
}
