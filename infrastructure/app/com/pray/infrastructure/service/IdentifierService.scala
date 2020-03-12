package com.pray.infrastructure.service

class IdentifierService(workerId: Long = 1L, dataCenterId: Long = 1L,
                        var sequence: Long = 0) {

  private[this] val epoch = 1288834974657L

  private[this] val sequenceBits = 12L

  private[this] val workerIdBits = 5L

  private[this] val dataCenterIdBits = 5L

  private[this] val workerIdShift = sequenceBits

  private[this] val dataCenterIdShift = sequenceBits + workerIdBits

  private[this] val timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits

  private[this] val sequenceMask = -1L ^ (-1L << sequenceBits)

  private[this] var lastTimestamp = -1L

  private def timeGen: Long = System.currentTimeMillis

  private def nextId(): Long = synchronized {
    var timestamp = timeGen
    if (timestamp < lastTimestamp) throw new Exception("InvalidSystemClock")
    if (lastTimestamp == timestamp) {
      sequence = (sequence + 1) & sequenceMask
      if (sequence == 0) timestamp = tilNextMillis(lastTimestamp)
    } else sequence = 0
    lastTimestamp = timestamp
    ((timestamp - epoch) << timestampLeftShift) |
      (dataCenterId << dataCenterIdShift) |
      (workerId << workerIdShift) | sequence
  }

  private def tilNextMillis(lastTimestamp: Long): Long = {
    var timestamp = timeGen
    while (timestamp <= lastTimestamp) {
      timestamp = timeGen
    }
    timestamp
  }

  def generate: Long = nextId()

}
