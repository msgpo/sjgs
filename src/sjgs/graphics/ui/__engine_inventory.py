def __engine_onLeftClick(inventory):
    # SLOT SWAPPING! 
    # @param i: The slot you are currently holding 
    # @param e: The slot you are trying to click on 
    for i in inventory.getSlots():
        # FILTER UNTIL I == THE SLOT YOU'RE HOLDING
        if not i.mouseClicked: continue
        for e in inventory.getSlots():
            # IF THE SECOND SLOT CONTAINS THE MOUSE POINTER, SWAP!
            if i != e and e.bounds.contains(Mouse.getClickX(), Mouse.getClickY()):
                inventory.swapSlots(i, e)
                return # NOTE: YOU MUST RETURN HERE OR IT WONT WORK!

    # GRAPHICAL SLOT MOVING!
    for i in inventory.getSlots():
        if i.isEmpty(): continue
        # SET MOUSELCICKED TO TRUE
        if not i.mouseClicked and i.bounds.contains(Mouse.getClickX(), Mouse.getClickY()):
            inventory.resetMouseClicked() # ONLY WANT 1 ITEM TO BE MOUSE CLICKED AT A TIME
            i.mouseClicked = True
            break
        
        # IF HOLDING AN ITEM, AND CLICK OUTSIDE OF THE INVENTORY BOUNDS --- DROP
        elif i.mouseClicked and not inventory.getBounds().contains(Mouse.getClickX(), Mouse.getClickY()):
            i.mouseClicked = False
            break
        # ELSE IF YOU JUST CLICKED SOMEWHERE IN THE INVEN WITHOUT BEING ON A SLOT LIKE AN IDIOT
        else: i.mouseClicked = False
                
    # FIXES BUGS -- ALWAYS RUN THIS
    inventory.resetPositions()
                
def __engine_add_item(itemId, quantity, inventory):
    # DONT ADD ITEM IF IT IS THE NULL PLACEHOLDER ITEM
    if itemId == EMPTY: return
    
    # CANT ADD ITEM IF THE INVEN IS FULL
    if __engine_inventory_is_full(itemId, quantity, inventory): return
            
    # IF THE ITEM IS STACKABLE, RUN THIS LOOP
    if inventory.isStackable(itemId):
        for i in inventory.getSlots():
            if i.itemId == itemId and (i.quantity + quantity) <= inventory.getStackableAmount(itemId):
                i.mouseClicked = False
                i.quantity += quantity
                inventory.resetPositions()
                return
                
    # IF THE ITEM IS NOT STACKABLE, OR FAILED THE ABOVE LOOP, RUN THIS LOOP
    for i in inventory.getSlots():
        if(i.isEmpty()):
            i.mouseClicked = False
            i.itemId = itemId
            i.quantity = quantity
            inventory.resetPositions()
            return

def __engine_remove_item(itemId, quantity, inventory):
    for slot in inventory.getSlots():
        if slot.itemId == itemId:
            temp = slot.quantity
            slot.quantity -= quantity
            quantity -= temp
            slot.mouseClicked = False
    
            if slot.quantity <= 0: slot.reset()
                
            # if the amount of things to remove has been satisfied
            if quantity <= 0: break
    inventory.resetPositions()
    
def __engine_remove_item_with_slot_number(itemId, quantity, slotNumber):
    for i in inventory.getSlots():
        if i.slotNumber == slotNumber:
            if i.itemId == itemId:
                i.quantity -= quantity
                i.mouseClicked = False
                if i.quantity <= 0: slot.reset()
                break
    inventory.resetPositions()

def __engine_inventory_is_full(itemId, quantity, inventory):
    if inventory.isStackable(itemId): # IF ITEM IS STACKABLE, RUN THIS LOOP
        for slot in inventory.getSlots():
            # CHECK IF THE (QUANTITY + THE ADDED QUANTITY) IS <= THE MAX QUANTITY
            WOULD_BE_QUANTITY = slot.quantity + quantity
            if slot.itemId == itemId and WOULD_BE_QUANTITY <= inventory.getStackableAmount(itemId): return False
        
    # IF IT IS NOT STACKABLE, OR THE ITEM FAILED THE FIRST LOOP, RUN THIS LOOP
    for slot in inventory.getSlots():
        if slot.itemId == 0: return False
            
    # IF NONE OF THE ABOVE CONDITIONS COULD BE MET, THE INVENTORY MUST BE FULL
    return True

def __engine_inventory_contains(itemId, quantity, inventory):
    count = 0
    for slot in inventory.getSlots():
        if slot.itemId == itemId: count += slot.quantity
    return count >= quantity