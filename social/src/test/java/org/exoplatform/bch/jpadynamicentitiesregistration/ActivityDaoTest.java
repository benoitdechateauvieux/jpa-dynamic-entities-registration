package org.exoplatform.bch.jpadynamicentitiesregistration;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by bdechateauvieux on 7/14/15.
 */
public class ActivityDaoTest {

    @Test
    public void test_saveActivity() {
        //Given
        ActivityDao dao = new ActivityDao();
        Activity activity = new Activity();
        //When
        EntityManagerService.get().startRequest();
        dao.create(activity);
        EntityManagerService.get().endRequest();
        //Then
        EntityManagerService.get().startRequest();
        assertThat(dao.findAll().size(), is(1));
        EntityManagerService.get().endRequest();
    }
}
