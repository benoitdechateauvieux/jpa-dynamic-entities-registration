package jpadynamicentitiesregistration;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Created by bdechateauvieux on 7/14/15.
 */
public class NewEntityDaoTest {

    @Test
    public void test_saveNewEntity() {
        //Given
        NewEntityDao dao = new NewEntityDao();
        NewEntity newEntity = new NewEntity();
        //When
        dao.create(newEntity);
        //Then
        assertThat(dao.findAll().size(), is(1));
    }
}
