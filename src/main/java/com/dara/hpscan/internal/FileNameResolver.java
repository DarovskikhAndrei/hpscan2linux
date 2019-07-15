package com.dara.hpscan.internal;

import com.dara.hpscan.StateService;

public interface FileNameResolver
{
    String getFileName(StateService instance);
}
