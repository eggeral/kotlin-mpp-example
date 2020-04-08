
import SwiftUI
import kotlin_mpp_common

struct ContentView: View {
    
    @State private var inputA = ""
    @State private var inputB = ""
    @State private var result = "Result:"

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
                self.result = CalculatorKt.add(inputA: self.inputA,inputB: self.inputB)
            }) {
                Text("Calculate")
            }
            .padding(.bottom)
            HStack {
                Text(self.result)
            }

        }
        
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
