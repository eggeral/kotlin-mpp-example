import SwiftUI
import kotlin_mpp_common

struct BoardView: View {

    @Binding var board: Board?
    @State var lastScaleValue: CGFloat = 1.0
    @State var scale: CGFloat = 1.0

    @ViewBuilder
    var body: some View {
        if self.board != nil {
            VStack(spacing: 2) {
                ForEach(0..<Int(self.board!.rows)) { rowIdx in
                    HStack(spacing: 2) {
                        ForEach(0..<Int(self.board!.columns)) { columnIdx in
                            self.createCell(board: self.board!, rowIdx: rowIdx, columnIdx: columnIdx)
                        }
                    }
                }
            }
            .gesture(MagnificationGesture()
            .onChanged{value in
                let delta = value / self.lastScaleValue
                self.lastScaleValue = value
                self.scale = self.scale * delta
            }.onEnded{value in
                self.lastScaleValue = 1.0
            })

        }
        else {
            EmptyView()
        }
    }

    private func createCell(board: Board, rowIdx: Int, columnIdx: Int) -> some View {
        let cell = board.cellAt(column: Int32(columnIdx), row: Int32(rowIdx))
        if (cell.alive) {
            return Rectangle()
            .fill(Color.gray)
            .frame(width: 10 * self.scale, height: 10 * self.scale)
        }
        return Rectangle()
        .fill(Color.white)
        .frame(width: 10 * self.scale, height: 10 * self.scale)
    }

}

struct BoardView_Previews: PreviewProvider {
    static var previews: some View {
        let game = Game()
        return BoardView(board: .constant(game.board))
    }
}
