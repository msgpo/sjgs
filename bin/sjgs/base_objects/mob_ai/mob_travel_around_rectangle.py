# mob will travel around a given rectangle -- think like in early stages of metroid
# territory === given rectangle
def mob_travel_around_rectangle_ai(mob, territory, speed, clockWise):
    fullBounds = mob.getFullBounds()
    rightBounds = mob.getRightBounds()
    leftBounds = mob.getLeftBounds()
    topBounds = mob.getTopBounds()
    bottomBounds = mob.getBottomBounds()
    
    top = territory.getTop()
    right = territory.getRight()
    left = territory.getLeft()
    bottom = territory.getBottom()
    
    if clockWise:
        if fullBounds.intersects(right) and not fullBounds.intersects(bottom):    
            mob.setVelocity(0, speed)
            mob.setX(territory.pos.x + territory.getWidth())
            mob.setY(restrict(mob.getY(), territory.getY() - 2))
        elif fullBounds.intersects(top): 
            mob.setVelocity(speed, 0)
            mob.setY(territory.pos.y - mob.getHeight())
            mob.setX(restrict(mob.getX(), territory.getX() - 1))
        elif fullBounds.intersects(left) and not fullBounds.intersects(top) and not topBounds.intersects(bottom):     
            mob.setVelocity(0, -speed)
            mob.setX(territory.pos.x - mob.getHeight())
        elif fullBounds.intersects(bottom):   
            mob.setVelocity(-speed, 0)
            mob.setY(territory.pos.y + 2 + mob.getHeight())
    else:
        if fullBounds.intersects(right) and not fullBounds.intersects(top):    
            mob.setVelocity(0, -speed)
            mob.setX(territory.pos.x + territory.getWidth())
            mob.setY(restrict(mob.getY(), territory.getY() - 2))
        elif fullBounds.intersects(top) and not fullBounds.intersects(left): 
            mob.setVelocity(-speed, 0)
            mob.setY(territory.pos.y - mob.getHeight())
        elif fullBounds.intersects(bottom) and not fullBounds.intersects(right):     
            mob.setVelocity(speed, 0)
            mob.setY(territory.pos.y + mob.getHeight() + 2)
        elif fullBounds.intersects(left):   
            mob.setVelocity(0, speed)
            mob.setX(territory.pos.x - mob.getHeight())