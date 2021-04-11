//
//  ContentView.swift
//  Mediaforum-iOS
//
//  Created by Dreamtowards on 2020/5/23.
//  Copyright © 2020 Dreamtowards. All rights reserved.
//

import SwiftUI

struct ContentView: View {
    
    var body: some View {
        TabView {
            
            PageHome().tabItem({
                Image(systemName: "house.fill")
                Text("Home")
            })
            
            VStack {
                Text("Page")
            }.tabItem({
                Image(systemName: "safari.fill")
                Text("Hoting")
            })
            
            VStack {
                Text("Page")
            }.tabItem({
                Image(systemName: "tray.2.fill")
                Text("Subs")
            })
            
            VStack {
                Text("Page")
            }.tabItem({
                Image(systemName: "folder.fill")
                Text("Libs")
            })
            
            VStack {
                Text("Page")
            }.tabItem({
                Image(systemName: "person.circle.fill")
                Text("Person")
            })
        }
//        .colorScheme(.dark)
//        .edgesIgnoringSafeArea(.top)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
