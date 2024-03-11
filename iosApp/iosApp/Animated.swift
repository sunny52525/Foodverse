import Foundation
 
import UIKit

class AnimatedCircleView: UIView {

    private var circleLayer: CAShapeLayer!
    private var gradientLayer: CAGradientLayer!
    private var displayLink: CADisplayLink?
    private var animationDuration: TimeInterval = 4.0

    var startColor: UIColor = UIColor(red: 138/255, green: 58/255, blue: 185/255, alpha: 1.0) // dark_purple #8a3ab9
    var endColor: UIColor = UIColor(red: 205/255, green: 72/255, blue: 107/255, alpha: 1.0) // pinkish_red #cd486b
    var strokeWidth: CGFloat = 6.0

    override init(frame: CGRect) {
        super.init(frame: frame)
        setupView()
    }

    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setupView()
    }

    private func setupView() {
        gradientLayer = CAGradientLayer()
        gradientLayer.startPoint = CGPoint(x: 0.5, y: 0)
        gradientLayer.endPoint = CGPoint(x: 0.5, y: 1)
        gradientLayer.colors = [startColor.cgColor, endColor.cgColor]
        gradientLayer.type = .conic
        layer.addSublayer(gradientLayer)

        circleLayer = CAShapeLayer()
        circleLayer.fillColor = nil
        circleLayer.lineWidth = strokeWidth
        circleLayer.lineCap = .round
        circleLayer.strokeColor = UIColor.black.cgColor // Default color, will be masked by gradient
        gradientLayer.mask = circleLayer
    }

    override func layoutSubviews() {
        super.layoutSubviews()
        gradientLayer.frame = bounds
        circleLayer.frame = bounds
        updatePath()
        startAnimation()
    }

    private func updatePath() {
        let radius = min(bounds.width, bounds.height) / 2 - strokeWidth / 2
        let center = CGPoint(x: bounds.midX, y: bounds.midY)
        let path = UIBezierPath(arcCenter: center, radius: radius, startAngle: -CGFloat.pi / 2, endAngle: CGFloat.pi * 1.5, clockwise: true)
        circleLayer.path = path.cgPath
    }

    private func startAnimation() {
        displayLink = CADisplayLink(target: self, selector: #selector(updateAnimation))
        displayLink?.add(to: .main, forMode: .default)

        let rotationAnimation = CABasicAnimation(keyPath: "transform.rotation.z")
        rotationAnimation.byValue = NSNumber(value: Double.pi * 2)
        rotationAnimation.duration = animationDuration
        rotationAnimation.isCumulative = true
        rotationAnimation.repeatCount = .infinity
        gradientLayer.add(rotationAnimation, forKey: "rotationAnimation")
    }

    @objc private func updateAnimation() {
        let phase = (CACurrentMediaTime().truncatingRemainder(dividingBy: animationDuration) / animationDuration) * 2.0 * CGFloat.pi
        let value = (sin(phase) + 1) * 0.5 * (10 - 1) + 1 // Create a smoothly varying value between 1 and 10
        circleLayer.lineDashPattern = [NSNumber(value: Float(value)), NSNumber(value: Float(value))]
    }

    deinit {
        displayLink?.invalidate()
        circleLayer.removeAllAnimations()
        gradientLayer.removeAllAnimations()
    }
}
