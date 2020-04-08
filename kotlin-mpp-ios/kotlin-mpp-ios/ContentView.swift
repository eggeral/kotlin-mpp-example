
import Combine
import SwiftUI
import kotlin_mpp_common

class BoardStore: ObservableObject {
    
    private let size:Int32 = 15;
    
    let objectWillChange = ObservableObjectPublisher()
    
    var board: Board
    
    init() {
        board = Board.init(columns: size, rows: size)
        let cells = BoardKt.cells(
            """
            ***_*
            *____
            ___**
            _**_*
            *_*_*
            """)
        let cellsCentered = BoardKt.translatedTo(
            cells, column: size / 2 - 2, row: size / 2 - 2 )
        board.setCells(cellDefinitions: cellsCentered)

    }
    
    func calculateNextGeneration() {
        self.board.calculateNextGeneration()
        self.objectWillChange.send()
    }

}

struct ContentView: View {
    
    @ObservedObject var boardStore = BoardStore()
    let timer = Timer.publish(every: 0.5, on: .main, in: .common).autoconnect()

    var body: some View {
        
        BoardView(board: $boardStore.board).onReceive(self.timer) { time in
            self.boardStore.calculateNextGeneration()
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
