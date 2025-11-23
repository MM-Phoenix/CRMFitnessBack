package crmfitness.project.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SocketKey {

    private final String session;
    private final String urlPath;
}
