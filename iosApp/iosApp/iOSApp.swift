import SwiftUI
import shared

@main
struct iOSApp: App {
    let databaseDriverFactory = DatabaseDriverFactory()
    
	var body: some Scene {
		WindowGroup {
            ContentView(viewModel: ContentView.ViewModel(databaseDriverFactory: databaseDriverFactory))
		}
	}
}
