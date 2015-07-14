package org.exoplatform.bch.jpadynamicentitiesregistration;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Created by bdechateauvieux on 7/14/15.
 */
public class TaskDaoTest {

    @Test
    public void test_saveTask() {
        //Given
        TaskDao dao = new TaskDao();
        Task task = new Task();
        //When
        EntityManagerService.get().startRequest();
        dao.create(task);
        EntityManagerService.get().endRequest();
        //Then
        EntityManagerService.get().startRequest();
        assertThat(dao.findAll().size(), is(1));
        EntityManagerService.get().endRequest();
    }
}
