
import Combine
import SwiftUI
import kotlin_mpp_common

class BoardStore: ObservableObject {

    let columns: Int32 = 30
    let rows: Int32 = 50

    let objectWillChange = ObservableObjectPublisher()

    var board: Board

    init() {
        self.board = Board.init(columns: columns, rows: rows)
        let cells = BoardKt.cells(
            """
            ***_*
            *____
            ___**
            _**_*
            *_*_*
            """)
        let cellsCentered = BoardKt.translatedTo(
            cells, column: columns / 2 - 2, row: rows / 2 - 2 )
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
