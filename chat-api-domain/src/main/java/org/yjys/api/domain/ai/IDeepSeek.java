package org.yjys.api.domain.ai;

import java.io.IOException;

public interface IDeepSeek {
    String doDeepSeek(String question) throws IOException;
}
