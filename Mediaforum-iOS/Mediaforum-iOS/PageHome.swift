//
//  PageHome.swift
//  Mediaforum-iOS
//
//  Created by Dreamtowards on 2020/5/28.
//  Copyright © 2020 Dreamtowards. All rights reserved.
//

import SwiftUI

struct PageHome: View {
    
    var body: some View {
        VStack {
            
            Text("Home")
                .font(.largeTitle).bold()
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.leading, 20)
                .padding(.top, 48)
            
            VStack {
                Text("Title of a post")
            }
            
            Spacer()
            
        }
    }
}

struct PageHome_Previews: PreviewProvider {
    static var previews: some View {
        PageHome()
    }
}
