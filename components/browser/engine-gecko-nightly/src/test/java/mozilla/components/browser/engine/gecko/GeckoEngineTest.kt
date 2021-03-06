/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package mozilla.components.browser.engine.gecko

import mozilla.components.concept.engine.UnsupportedSettingException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.GeckoRuntimeSettings
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class GeckoEngineTest {

    private val runtime: GeckoRuntime = mock(GeckoRuntime::class.java)

    @Test
    fun testCreateView() {
        assertTrue(GeckoEngine(runtime).createView(RuntimeEnvironment.application) is GeckoEngineView)
    }

    @Test
    fun testCreateSession() {
        assertTrue(GeckoEngine(runtime).createSession() is GeckoEngineSession)
    }

    @Test
    fun testName() {
        assertEquals("Gecko", GeckoEngine(runtime).name())
    }

    @Test
    fun testSettings() {
        val runtime = mock(GeckoRuntime::class.java)
        val runtimeSettings = mock(GeckoRuntimeSettings::class.java)
        `when`(runtimeSettings.javaScriptEnabled).thenReturn(true)
        `when`(runtime.settings).thenReturn(runtimeSettings)
        val engine = GeckoEngine(runtime)

        assertTrue(engine.settings.javascriptEnabled)
        engine.settings.javascriptEnabled = false
        verify(runtimeSettings).javaScriptEnabled = false

        try {
            engine.settings.domStorageEnabled
            fail("Expected UnsupportedOperationException")
        } catch (e: UnsupportedSettingException) { }

        try {
            engine.settings.domStorageEnabled = false
            fail("Expected UnsupportedOperationException")
        } catch (e: UnsupportedSettingException) { }
    }
}