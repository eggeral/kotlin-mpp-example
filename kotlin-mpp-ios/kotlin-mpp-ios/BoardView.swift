import SwiftUI
import kotlin_mpp_common

struct BoardView: View {

    @Binding var board: Board

    var body: some View {

        VStack(spacing: 2) {
            ForEach(0..<Int(self.board.rows)) { rowIdx in
                HStack(spacing: 2) {
                    ForEach(0..<Int(self.board.columns)) { columnIdx in
                        self.createCell(rowIdx: rowIdx, columnIdx: columnIdx)
                    }
                }
            }
        }
    }

    private func createCell(rowIdx: Int, columnIdx: Int) -> some View {
        let cell = self.board.cellAt(column: Int32(columnIdx), row: Int32(rowIdx))
        if (cell.alive) {
            return Rectangle()
            .fill(Color.gray)
            .frame(width: 10, height: 10)
        }
        return Rectangle()
        .fill(Color.white)
        .frame(width: 10, height: 10)
    }

}

struct BoardView_Previews: PreviewProvider {
    static var previews: some View {
        let columns: Int32 = 30
        let rows: Int32 = 50
        let board = Board.init(columns: columns, rows: rows)
        let cells = BoardKt.cells(
            """
            ***_*
            *____
            ___**
            _**_*
            *_*_*
            """)
        let cellsCentered = BoardKt.translatedTo(cells, column: columns / 2 - 2, row: rows / 2 - 2 )
        board.setCells(cellDefinitions: cellsCentered)
        return BoardView(board: .constant(board))
    }
}
