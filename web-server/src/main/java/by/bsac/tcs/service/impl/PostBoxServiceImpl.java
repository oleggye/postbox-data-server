package by.bsac.tcs.service.impl;

import by.bsac.tcs.model.PostBox;
import by.bsac.tcs.service.PostBoxService;
import by.bsac.tcs.service.exception.PostBoxServiceException;
import by.bsac.tcs.service.exception.ServiceValidationException;

public class PostBoxServiceImpl implements PostBoxService {

  @Override
  public void registration(PostBox postBox)
      throws PostBoxServiceException, ServiceValidationException {
    throw new UnsupportedOperationException("Not implemented!");
  }
}