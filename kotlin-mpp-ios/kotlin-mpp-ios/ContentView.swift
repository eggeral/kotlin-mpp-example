
import Combine
import SwiftUI
import kotlin_mpp_common

class BoardStore: ObservableObject {

    let objectWillChange = ObservableObjectPublisher()

    var board: Board? = nil

    func calculateNextGeneration() {
        self.objectWillChange.send()
    }

}

struct ContentView: View {

    let game = Game()

    @State var pauseResumeButtonText = "Pause"
    @ObservedObject var boardStore = BoardStore()

    var body: some View {
        
        NavigationView {
            BoardView(board: $boardStore.board)
                .navigationBarItems(trailing:
                    Button(self.pauseResumeButtonText) {
                        if self.game.running {
                            self.game.pause()
                            self.pauseResumeButtonText = "Resume"
                        } else {
                            self.game.resume()
                            self.pauseResumeButtonText = "Pause"
                        }
                    }
            )
        }
        .onAppear(perform: {
            self.boardStore.board = self.game.board
            self.game.afterNextGenerationCalculated = {
                self.boardStore.calculateNextGeneration()
            }
            self.game.resume()
        })
        .onDisappear(perform: {
            self.game.pause()
        })
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
