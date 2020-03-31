
import SwiftUI

struct ContentView: View {
    
    @State private var inputA = ""
    @State private var inputB = ""
    @State private var result = 0.0

    var body: some View {
        
        VStack {
            HStack {
                TextField("Number A", text: $inputA)
                    .padding([.leading, .bottom])
                    .keyboardType(.numberPad)
            }
            HStack {
                TextField("Number B", text: $inputB)
                    .padding([.leading, .bottom])
                    .keyboardType(.numberPad)
            }
            Button(action: {
                let numberA = Double(self.inputA) ?? 0.0
                let numberB = Double(self.inputB) ?? 0.0
                self.result = numberA + numberB
            }) {
                Text("Calculate")
            }
            .padding(.bottom)
            HStack {
                Text("Result: \(self.result)")
            }

        }
        
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
