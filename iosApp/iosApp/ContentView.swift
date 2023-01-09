import SwiftUI
import shared

struct ContentView: View {
    @ObservedObject private(set) var viewModel: ViewModel
    
    var body: some View {
        VStack {
            Text(viewModel.error)
                .foregroundColor(.red)
            
            List(viewModel.rocketLaunches, id: \.id) { rocketLaunch in
                VStack {
                    Text(rocketLaunch.missionName)
                        .foregroundColor(.black)
                    
                    Text(rocketLaunch.launchDateUTC)
                        .foregroundColor(.gray)
                }
                .cornerRadius(20)
                .shadow(color: Color.black.opacity(0.2), radius: 20, x: 0, y: 0)
                .padding(10)
                .multilineTextAlignment(.leading)
                .listRowInsets(EdgeInsets(
                    top: 4,
                    leading: 4,
                    bottom: 4,
                    trailing: 4)
                )
            }
        }.background(Color(UIColor(hex: 0xf2f7fd)))
    }
}

extension ContentView {
    class ViewModel: ObservableObject {
        let databaseDriverFactory: DatabaseDriverFactory
        @Published var rocketLaunches: [RocketLaunch] = []
        @Published var error: String = ""
        
        init(databaseDriverFactory: DatabaseDriverFactory) {
            self.databaseDriverFactory = databaseDriverFactory
            let database = AppDatabase(databaseDriverFactory: self.databaseDriverFactory)
            let cache = Cache(rocketQuery: database.rocketLaunchQueries)
            let api = Api()
            let rocketsService = RocketsService(cache: cache, api: api)
            
            rocketsService.get { rocketLaunches, error in
                DispatchQueue.main.async {
                    if let rocketLaunches = rocketLaunches {
                        self.rocketLaunches = rocketLaunches
                    } else {
                        self.rocketLaunches = []
                        self.error = error?.localizedDescription ?? "error"
                    }
                }
            }
        }
    }
}
