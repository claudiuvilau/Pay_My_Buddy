package com.openclassrooms.pay_my_buddy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.openclassrooms.pay_my_buddy.model.Descriptions;
import com.openclassrooms.pay_my_buddy.repository.DescriptionsRepository;
import com.openclassrooms.pay_my_buddy.service.DescriptionsService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class DescriptionsServiceTests {

  @Autowired
  private DescriptionsService descriptionsService;

  @Autowired
  private Descriptions descriptions;

  @MockBean
  DescriptionsRepository descriptionsRepository;

  @Test
  public void testGetDescriptionNoListDescription() {
    //descriptions = new Descriptions();
    //descriptions.setIdDescriptions(99);
    //descriptions.setDescription("null");

    Iterable<Descriptions> iterableDescriptions = new ArrayList<Descriptions>();
    when(descriptionsRepository.findAll()).thenReturn(iterableDescriptions);

    descriptions = descriptionsService.getDescription("");

    assertEquals(null, descriptions);
  }

  @Test
  public void testGetDescriptionListDescriptions() {
    descriptions = new Descriptions();
    descriptions.setIdDescriptions(99);
    descriptions.setDescription("test");

    List<Descriptions> descriptionsList = new ArrayList<Descriptions>();
    descriptionsList.add(descriptions);

    when(descriptionsRepository.findAll())
      .thenReturn((Iterable<Descriptions>) descriptionsList);

    Descriptions descriptionsNew = descriptionsService.getDescription("test");

    assertEquals("test", descriptionsNew.getDescription());
  }
}
