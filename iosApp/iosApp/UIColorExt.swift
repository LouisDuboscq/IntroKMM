//
//  UIColorExt.swift
//  iosApp
//
//  Created by Louis Duboscq on 07/01/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import Foundation
 
extension UIColor {
    convenience init(hex: Int) {
        self.init(red: CGFloat((hex >> 16) & 0xff) / 255.0, green: CGFloat((hex >> 8) & 0xff) / 255.0, blue: CGFloat(hex & 0xff) / 255.0, alpha: 1)
    }
}
