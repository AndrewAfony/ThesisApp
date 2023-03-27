package andrewafony.thesis.application

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private lateinit var testNavigationCommunication: TestNavigationCommunication
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        testNavigationCommunication = TestNavigationCommunication()
        mainViewModel = MainViewModel(testNavigationCommunication)
    }

    @Test
    fun init_auth() {
        mainViewModel.init(true)

        assertEquals(testNavigationCommunication.count, 1)
        assertTrue(testNavigationCommunication.navigation is Navigation.Replace)
        assertTrue(testNavigationCommunication.screen is Screen.Home)

        mainViewModel.init(false)
        assertEquals(testNavigationCommunication.count, 1)
        assertTrue(testNavigationCommunication.navigation is Navigation.Replace)
        assertTrue(testNavigationCommunication.screen is Screen.Home)
    }

    @Test
    fun init_not_auth() {

    }

}

private class TestNavigationCommunication: NavigationCommunication {

    var navigation: Navigation = Navigation.Add(Screen.Home)
    var screen: Screen = Screen.Home
    var count = 0

    override fun observe(owner: LifecycleOwner, observer: Observer<Navigation>)  = Unit

    override fun map(data: Navigation) {
        navigation = data
        screen = data.screen()
        count++
    }
}