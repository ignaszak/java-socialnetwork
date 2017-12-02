package net.ignaszak.socialnetwork.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import net.ignaszak.socialnetwork.SocialnetworkApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@TestPropertySource(locations="classpath:test.properties")
@RunWith(SpringRunner.class)
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class
})
@SpringBootTest(classes = SocialnetworkApplication.class)
@DirtiesContext
abstract public class RepositoryTestsInitializer {
}
