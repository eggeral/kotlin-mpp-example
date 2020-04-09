
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
    @ObservedObject var boardStore = BoardStore()

    var body: some View {
        BoardView(board: $boardStore.board)
            .onAppear(perform: {
                self.boardStore.board = self.game.board
                self.game.afterNextGenerationCalculated = {
                    self.boardStore.calculateNextGeneration()
                }
                self.game.start()
            })
            .onDisappear(perform: {
                self.game.stop()
            })
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
