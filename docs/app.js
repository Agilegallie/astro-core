/* ==========================================
   ASTRO-CORE Landing Page Simulator Controller (app.js)
   ========================================== */

document.addEventListener("DOMContentLoaded", () => {
    // Simulator Screens
    const screens = {
        home: document.getElementById("sim-screen-home"),
        capture: document.getElementById("sim-screen-capture"),
        analysis: document.getElementById("sim-screen-analysis"),
        success: document.getElementById("sim-screen-success")
    };

    // Sidebar & Navigation buttons
    const sidebarButtons = document.querySelectorAll(".sim-nav-btn");
    const bottomNavItems = document.querySelectorAll(".phone-bottom-nav .nav-item");

    // Action buttons inside mockup
    const actionButtons = {
        quickCapture: document.querySelector('[data-action="go-to-capture"]'),
        shutter: document.querySelector('[data-action="capture-image"]'),
        submit: document.querySelector('[data-action="submit-uplink"]'),
        copy: document.querySelector('[data-action="copy-ref-id"]'),
        reset: document.querySelector('[data-action="reset-sim"]')
    };

    // Animation elements
    const cameraFlash = document.querySelector(".camera-flash");
    const loadingOverlay = document.querySelector(".uplink-loading-overlay");
    const toast = document.querySelector(".clipboard-success-toast");

    // State Variables
    let currentScreen = "home";

    // 1. Navigation Controller function
    function switchScreen(screenName) {
        if (!screens[screenName]) return;

        // Update screens active classes
        Object.keys(screens).forEach(key => {
            if (key === screenName) {
                screens[key].classList.add("active");
            } else {
                screens[key].classList.remove("active");
            }
        });

        // Update sidebar nav button active classes
        sidebarButtons.forEach(btn => {
            if (btn.getAttribute("data-screen") === screenName) {
                btn.classList.add("active");
            } else {
                btn.classList.remove("active");
            }
        });

        // Update bottom navigation active class
        bottomNavItems.forEach(item => {
            if (item.getAttribute("data-nav-target") === screenName) {
                item.classList.add("active");
            } else {
                item.classList.remove("active");
            }
        });

        currentScreen = screenName;
    }

    // 2. Event Listeners for Nav buttons
    sidebarButtons.forEach(btn => {
        btn.addEventListener("click", () => {
            const screen = btn.getAttribute("data-screen");
            switchScreen(screen);
        });
    });

    bottomNavItems.forEach(item => {
        item.addEventListener("click", () => {
            const target = item.getAttribute("data-nav-target");
            switchScreen(target);
        });
    });

    // 3. Flow Actions

    // Home -> Capture
    if (actionButtons.quickCapture) {
        actionButtons.quickCapture.addEventListener("click", () => {
            switchScreen("capture");
        });
    }

    // Capture -> Analysis (with camera flash effect)
    if (actionButtons.shutter) {
        actionButtons.shutter.addEventListener("click", () => {
            if (cameraFlash) {
                // Trigger Camera Flash
                cameraFlash.classList.add("flash-active");
                
                setTimeout(() => {
                    cameraFlash.classList.remove("flash-active");
                    switchScreen("analysis");
                }, 300);
            } else {
                switchScreen("analysis");
            }
        });
    }

    // Analysis -> Success (with establishment uplink loader)
    if (actionButtons.submit) {
        actionButtons.submit.addEventListener("click", () => {
            if (loadingOverlay) {
                // Open loader overlay
                loadingOverlay.classList.add("loading-active");
                
                setTimeout(() => {
                    loadingOverlay.classList.remove("loading-active");
                    switchScreen("success");
                }, 2500); // Wait 2.5s to simulate uplink propagation
            } else {
                switchScreen("success");
            }
        });
    }

    // Copy Reference ID
    if (actionButtons.copy) {
        actionButtons.copy.addEventListener("click", () => {
            const refIdText = "REF_ID_90812_SEC_4";
            
            // Web Clipboard API
            navigator.clipboard.writeText(refIdText).then(() => {
                if (toast) {
                    toast.classList.add("toast-active");
                    setTimeout(() => {
                        toast.classList.remove("toast-active");
                    }, 2000);
                }
            }).catch(err => {
                console.error("Failed to copy text: ", err);
            });
        });
    }

    // Success -> Reset to Home
    if (actionButtons.reset) {
        actionButtons.reset.addEventListener("click", () => {
            // Reset textareas in analysis screen
            const textarea = document.querySelector(".sim-textarea");
            if (textarea) textarea.value = "";
            switchScreen("home");
        });
    }
});
